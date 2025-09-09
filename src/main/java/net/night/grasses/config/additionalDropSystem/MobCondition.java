package net.night.grasses.config.additionalDropSystem;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public record MobCondition(EntityType<?> entityType, Map<String, Object> mobConditions) {
    private static final Map<EntityType<?>, Map<String, Method>> methodCache = new ConcurrentHashMap<>();
    private static final Set<String> IGNORED_KEYS = Set.of("addAdditionalDropMode");

    public MobCondition(EntityType<?> entityType, Map<String, Object> mobConditions) {
        this.entityType = entityType;
        this.mobConditions = (mobConditions == null) ? Map.of() : mobConditions;
    }

    public boolean matches(LivingEntity livingEntity) {
        if (!Objects.equals(livingEntity.getType(), entityType))
            return false;

        if (mobConditions.isEmpty())
            return true;

        Object conditions = mobConditions.get("conditions");
        if (conditions instanceof List<?> conditionsList) {
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

                Object actualValue = getPropertyValue(livingEntity, conditionKey);
                if (actualValue == null)
                    return false;

                if (!valuesMatch(actualValue, conditionValue))
                    return false;
            }
            return true;
        }
        return false;
    }

    private Object getPropertyValue(LivingEntity livingEntity, String conditionKey) {
        EntityType<?> entityType = livingEntity.getType();
        Map<String, Method> cachedMethods = methodCache.computeIfAbsent(entityType, k -> new ConcurrentHashMap<>());

        Method method = cachedMethods.get(conditionKey);
        if (method == null && !cachedMethods.containsKey(conditionKey)) {
            method = findMethodViaReflection(livingEntity.getClass(), conditionKey);

            if (method == null)
                return null;
            else
                cachedMethods.put(conditionKey, method);
        }

        if (method == null)
            return null;

        try {
            Object value = method.invoke(livingEntity);
            if (value != null && value.getClass().getName().contains("Component")) {
                Method getStringMethod = value.getClass().getMethod("getString");
                return getStringMethod.invoke(value);
            }
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    private Method findMethodViaReflection(Class<?> clazz, String conditionKey) {
        try {
            try {
                return clazz.getMethod(conditionKey);
            } catch (NoSuchMethodException e1) {
                String capitalized = capitalize(conditionKey);

                try {
                    return clazz.getMethod("is" + capitalized);
                } catch (NoSuchMethodException e2) {
                    return clazz.getMethod("get" + capitalized);
                }
            }
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private boolean valuesMatch(Object actualValue, Object conditionValue) {
        if (actualValue == null || conditionValue == null)
            return false;

        if (actualValue instanceof Boolean && conditionValue instanceof Boolean)
            return actualValue.equals(conditionValue);

        if (actualValue instanceof Number && conditionValue instanceof Number)
            return ((Number) actualValue).doubleValue() == ((Number) conditionValue).doubleValue();

        boolean equalsIgnoreCase = actualValue.toString().equalsIgnoreCase(conditionValue.toString());
        if (actualValue instanceof Number && conditionValue instanceof String) {
            try {
                double expectedNum = Double.parseDouble((String) conditionValue);
                return ((Number) actualValue).doubleValue() == expectedNum;
            } catch (NumberFormatException ignored) {
                return equalsIgnoreCase;
            }
        }

        return equalsIgnoreCase;
    }

    private String capitalize(String conditionKey) {
        if (conditionKey == null || conditionKey.isEmpty())
            return conditionKey;
        return Character.toUpperCase(conditionKey.charAt(0)) + conditionKey.substring(1);
    }
}
