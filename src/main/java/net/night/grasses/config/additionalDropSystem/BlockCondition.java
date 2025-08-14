package net.night.grasses.config.additionalDropSystem;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;
import java.util.Objects;

public record BlockCondition(Block block, Map<String, Object> stateConditions) { // stateConditions => property/property value

    public BlockCondition(Block block, Map<String, Object> stateConditions) {
        this.block = block;
        this.stateConditions = (stateConditions == null) ? Map.of() : stateConditions;
    }

    public boolean matches(BlockState state) {
        if (!Objects.equals(state.getBlock(), block))
            return false;

        if (stateConditions.isEmpty())
            return true;

        for (Map.Entry<String, Object> entry : stateConditions.entrySet()) {
            String propName = entry.getKey();
            Object expectedValue = entry.getValue();

            Property<?> prop = state.getBlock().getStateDefinition().getProperty(propName);
            if (prop == null)
                return false;

            Comparable<?> stateValue = state.getValue(prop);

            if (!propertyValueEquals(stateValue, expectedValue))
                return false;
        }
        return true;
    }

    private static boolean propertyValueEquals(Comparable<?> stateValue, Object expectedValue) {
        if (stateValue == null || expectedValue == null)
            return false;

        //(int, long, etc.)
        if (stateValue instanceof Number && expectedValue instanceof Number) {
            return ((Number) stateValue).longValue() == ((Number) expectedValue).longValue();
        }
        if (stateValue.getClass().isInstance(expectedValue))
            return stateValue.equals(expectedValue);

        //boolean
        if (stateValue instanceof Boolean && expectedValue instanceof Boolean)
            return stateValue.equals(expectedValue);

        // Enum
        if (stateValue instanceof Enum<?>) {
            return stateValue.toString().equals(expectedValue.toString());
        }

        if (stateValue instanceof String)
            return stateValue.equals(expectedValue.toString());

        if (stateValue instanceof Number && expectedValue instanceof String) {
            try {
                long expectedLong = Long.parseLong((String) expectedValue);
                return ((Number) stateValue).longValue() == expectedLong;
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
                ", stateConditions=" + stateConditions +
                '}';
    }
}

