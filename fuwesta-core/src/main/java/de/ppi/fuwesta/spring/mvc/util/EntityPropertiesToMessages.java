package de.ppi.fuwesta.spring.mvc.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * Add all Entity-Information to messages, the key has the form
 * db.&lt;simpleclassname&gt;.&lt;propertyname&gt;.
 * 
 */
public class EntityPropertiesToMessages {

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(EntityPropertiesToMessages.class);

    /**
     * the messages.
     */
    private final Properties messages = new Properties();

    private final String[] packageNames;

    private boolean analyzed = false;

    public EntityPropertiesToMessages(String... packageName) {
        this.packageNames = packageName;
    }

    public void analyze() {
        final List<Class<?>> entities = new ArrayList<>();
        for (String packageName : packageNames) {

            try {
                entities.addAll(findEntities(packageName));
            } catch (IOException e) {
                LOG.error("Error finding entities-classes", e);
            }
        }
        for (Class<?> entity : entities) {
            List<Field> fields = getAllFields(entity);
            for (Field field : fields) {
                final String key =
                        "db." + entity.getSimpleName() + "." + field.getName();
                messages.put(key, field.getName());
            }

        }
        analyzed = true;
    }

    public Properties getProperties() {
        if (!analyzed) {
            analyze();
        }
        return messages;
    }

    private List<Class<?>> findEntities(String basePackage) throws IOException {
        ResourcePatternResolver resourcePatternResolver =
                new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory =
                new CachingMetadataReaderFactory(resourcePatternResolver);

        final List<Class<?>> candidates = new ArrayList<Class<?>>();
        final String packageSearchPath =
                ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + resolveBasePackage(basePackage) + "/" + "**/*.class";
        final Resource[] resources =
                resourcePatternResolver.getResources(packageSearchPath);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader metadataReader =
                        metadataReaderFactory.getMetadataReader(resource);
                try {
                    if (isCandidate(metadataReader)) {
                        candidates.add(Class.forName(metadataReader
                                .getClassMetadata().getClassName()));
                    }
                } catch (ClassNotFoundException cnfE) {
                    LOG.error("Error finding entity-classes", cnfE);
                }
            }
        }
        return candidates;
    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils
                .resolvePlaceholders(basePackage));
    }

    private boolean isCandidate(MetadataReader metadataReader)
            throws ClassNotFoundException {
        Class<?> c =
                Class.forName(metadataReader.getClassMetadata().getClassName());
        if (c.getAnnotation(Entity.class) != null) {
            return true;
        }
        return false;
    }

    private List<Field> getAllFields(Class<?> type) {
        final List<Field> fields = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                fields.add(field);
            }
        }

        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
    }
}
