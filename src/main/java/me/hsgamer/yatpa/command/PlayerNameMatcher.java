package me.hsgamer.yatpa.command;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class PlayerNameMatcher {
    private static final int MAX_PATTERN_LENGTH = 64;

    private PlayerNameMatcher() {
    }

    static Player find(Collection<? extends Player> onlinePlayers, String input) {
        for (Player player : onlinePlayers) {
            if (player.getName().equalsIgnoreCase(input)) {
                return player;
            }
        }

        Player regexMatch = uniqueRegexMatch(onlinePlayers, input);
        if (regexMatch != null) {
            return regexMatch;
        }
        if (input.length() > MAX_PATTERN_LENGTH) {
            return null;
        }

        String normalizedInput = input.toLowerCase(Locale.ROOT);
        int allowedDistance = normalizedInput.length() <= 4 ? 1 : 2;
        int bestDistance = allowedDistance + 1;
        List<Player> bestMatches = new ArrayList<>();
        for (Player player : onlinePlayers) {
            int distance = damerauLevenshtein(
                    normalizedInput,
                    player.getName().toLowerCase(Locale.ROOT)
            );
            if (distance < bestDistance) {
                bestDistance = distance;
                bestMatches.clear();
                bestMatches.add(player);
            } else if (distance == bestDistance) {
                bestMatches.add(player);
            }
        }
        return bestDistance <= allowedDistance && bestMatches.size() == 1
                ? bestMatches.get(0)
                : null;
    }

    private static Player uniqueRegexMatch(Collection<? extends Player> onlinePlayers, String input) {
        if (input.length() > MAX_PATTERN_LENGTH) {
            return null;
        }

        final Pattern pattern;
        try {
            pattern = Pattern.compile(input, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException ignored) {
            return null;
        }

        Player match = null;
        for (Player player : onlinePlayers) {
            if (!pattern.matcher(player.getName()).matches()) {
                continue;
            }
            if (match != null) {
                return null;
            }
            match = player;
        }
        return match;
    }

    private static int damerauLevenshtein(String first, String second) {
        int[][] distance = new int[first.length() + 1][second.length() + 1];
        for (int firstIndex = 0; firstIndex <= first.length(); firstIndex++) {
            distance[firstIndex][0] = firstIndex;
        }
        for (int secondIndex = 0; secondIndex <= second.length(); secondIndex++) {
            distance[0][secondIndex] = secondIndex;
        }

        for (int firstIndex = 1; firstIndex <= first.length(); firstIndex++) {
            for (int secondIndex = 1; secondIndex <= second.length(); secondIndex++) {
                int substitutionCost = first.charAt(firstIndex - 1) == second.charAt(secondIndex - 1) ? 0 : 1;
                distance[firstIndex][secondIndex] = Math.min(
                        Math.min(
                                distance[firstIndex - 1][secondIndex] + 1,
                                distance[firstIndex][secondIndex - 1] + 1
                        ),
                        distance[firstIndex - 1][secondIndex - 1] + substitutionCost
                );

                if (firstIndex > 1
                        && secondIndex > 1
                        && first.charAt(firstIndex - 1) == second.charAt(secondIndex - 2)
                        && first.charAt(firstIndex - 2) == second.charAt(secondIndex - 1)) {
                    distance[firstIndex][secondIndex] = Math.min(
                            distance[firstIndex][secondIndex],
                            distance[firstIndex - 2][secondIndex - 2] + substitutionCost
                    );
                }
            }
        }
        return distance[first.length()][second.length()];
    }
}
