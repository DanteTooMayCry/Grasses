package net.night.grasses.config.additionalDropSystem;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record BlockCondition(Block block, Map<String, Object> blockStateConditions) { // blockStateConditions => property/property value
    private static final Set<String> IGNORED_KEYS = Set.of("addAdditionalDropMode");

    public BlockCondition(Block block, Map<String, Object> blockStateConditions) {
        this.block = block;
        this.blockStateConditions = (blockStateConditions == null) ? Map.of() : blockStateConditions;
    }

    public boolean matches(BlockState blockState) {
        if (!Objects.equals(blockState.getBlock(), block))
            return false;

        if (blockStateConditions.isEmpty())
            return true;

        Object conditions = blockStateConditions.get("conditions");
        if (!(conditions instanceof List<?> conditionsList))
            return false;

        for (Object condition : conditionsList) {
            if (!(condition instanceof String conditionString))
                continue;

            int equalsIndex = conditionString.indexOf('=');
            if (equalsIndex <= 0 || equalsIndex >= conditionString.length() - 1)
                continue;

            String conditionKey = conditionString.substring(0, equalsIndex).trim();

            if (IGNORED_KEYS.contains(conditionKey))
                continue;

            String conditionValue = conditionString.substring(equalsIndex + 1).trim();

            Property<?> property = blockState.getBlock().getStateDefinition().getProperty(conditionKey);
            if (property == null)
                return false;

            Comparable<?> blockStateValue = blockState.getValue(property);

            if (!propertyValueEquals(blockStateValue, conditionValue))
                return false;
        }

        return true;
    }

    private static boolean propertyValueEquals(Comparable<?> blockStateValue, Object conditionValue) {
        if (blockStateValue == null || conditionValue == null)
            return false;

        if (blockStateValue instanceof Number && conditionValue instanceof Number)
            return ((Number) blockStateValue).longValue() == ((Number) conditionValue).longValue();

        if (blockStateValue.getClass().isInstance(conditionValue))
            return blockStateValue.equals(conditionValue);

        if (blockStateValue instanceof Boolean && conditionValue instanceof Boolean)
            return blockStateValue.equals(conditionValue);

        if (blockStateValue instanceof Enum<?>)
            return blockStateValue.toString().equals(conditionValue.toString());

        if (blockStateValue instanceof String)
            return blockStateValue.equals(conditionValue.toString());

        if (blockStateValue instanceof Number && conditionValue instanceof String) {
            try {
                long expectedLong = Long.parseLong((String) conditionValue);
                return ((Number) blockStateValue).longValue() == expectedLong;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "BlockCondition{" +
                "block=" + block +
                ", blockStateConditions=" + blockStateConditions +
                '}';
    }
}

