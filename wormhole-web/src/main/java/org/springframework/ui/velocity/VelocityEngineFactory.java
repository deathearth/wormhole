package org.springframework.ui.velocity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.log.CommonsLogLogChute;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Deprecated
public class VelocityEngineFactory
{
  protected final Log logger = LogFactory.getLog(getClass());
  private Resource configLocation;
  private final Map<String, Object> velocityProperties = new HashMap<String, Object>();
  private String resourceLoaderPath;
  private ResourceLoader resourceLoader = new DefaultResourceLoader();
  private boolean preferFileSystemAccess = true;
  private boolean overrideLogging = true;
  
  public void setConfigLocation(Resource configLocation)
  {
    this.configLocation = configLocation;
  }
  
  public void setVelocityProperties(Properties velocityProperties)
  {
    CollectionUtils.mergePropertiesIntoMap(velocityProperties, this.velocityProperties);
  }
  
  public void setVelocityPropertiesMap(Map<String, Object> velocityPropertiesMap)
  {
    if (velocityPropertiesMap != null) {
      this.velocityProperties.putAll(velocityPropertiesMap);
    }
  }
  
  public void setResourceLoaderPath(String resourceLoaderPath)
  {
    this.resourceLoaderPath = resourceLoaderPath;
  }
  
  public void setResourceLoader(ResourceLoader resourceLoader)
  {
    this.resourceLoader = resourceLoader;
  }
  
  protected ResourceLoader getResourceLoader()
  {
    return this.resourceLoader;
  }
  
  public void setPreferFileSystemAccess(boolean preferFileSystemAccess)
  {
    this.preferFileSystemAccess = preferFileSystemAccess;
  }
  
  protected boolean isPreferFileSystemAccess()
  {
    return this.preferFileSystemAccess;
  }
  
  public void setOverrideLogging(boolean overrideLogging)
  {
    this.overrideLogging = overrideLogging;
  }
  
  public VelocityEngine createVelocityEngine()
    throws IOException, VelocityException
  {
    VelocityEngine velocityEngine = newVelocityEngine();
    Map<String, Object> props = new HashMap<String, Object>(16);
    if (this.configLocation != null)
    {
      if (this.logger.isInfoEnabled()) {
        this.logger.info("Loading Velocity config from [" + this.configLocation + "]");
      }
      CollectionUtils.mergePropertiesIntoMap(PropertiesLoaderUtils.loadProperties(this.configLocation), props);
    }
    if (!this.velocityProperties.isEmpty()) {
      props.putAll(this.velocityProperties);
    }
    if (this.resourceLoaderPath != null) {
      initVelocityResourceLoader(velocityEngine, this.resourceLoaderPath);
    }
    if (this.overrideLogging) {
      velocityEngine.setProperty("runtime.log.logsystem", new CommonsLogLogChute());
    }
    for (Map.Entry<String, Object> entry : props.entrySet()) {
      velocityEngine.setProperty((String)entry.getKey(), entry.getValue());
    }
    postProcessVelocityEngine(velocityEngine);
    
    velocityEngine.init();
    
    return velocityEngine;
  }
  
  protected VelocityEngine newVelocityEngine()
    throws IOException, VelocityException
  {
    return new VelocityEngine();
  }
  
  protected void initVelocityResourceLoader(VelocityEngine velocityEngine, String resourceLoaderPath)
  {
    if (isPreferFileSystemAccess())
    {
      try
      {
        StringBuilder resolvedPath = new StringBuilder();
        String[] paths = StringUtils.commaDelimitedListToStringArray(resourceLoaderPath);
        for (int i = 0; i < paths.length; i++)
        {
          String path = paths[i];
          Resource resource = getResourceLoader().getResource(path);
          File file = resource.getFile();
          if (this.logger.isDebugEnabled()) {
            this.logger.debug("Resource loader path [" + path + "] resolved to file [" + file.getAbsolutePath() + "]");
          }
          resolvedPath.append(file.getAbsolutePath());
          if (i < paths.length - 1) {
            resolvedPath.append(',');
          }
        }
        velocityEngine.setProperty("resource.loader", "file");
        velocityEngine.setProperty("file.resource.loader.cache", "true");
        velocityEngine.setProperty("file.resource.loader.path", resolvedPath.toString());
      }
      catch (IOException ex)
      {
        if (this.logger.isDebugEnabled()) {
          this.logger.debug("Cannot resolve resource loader path [" + resourceLoaderPath + "] to [java.io.File]: using SpringResourceLoader", ex);
        }
        initSpringResourceLoader(velocityEngine, resourceLoaderPath);
      }
    }
    else
    {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("File system access not preferred: using SpringResourceLoader");
      }
      initSpringResourceLoader(velocityEngine, resourceLoaderPath);
    }
  }
  
  protected void initSpringResourceLoader(VelocityEngine velocityEngine, String resourceLoaderPath)
  {
    velocityEngine.setProperty("resource.loader", "spring");
    
    velocityEngine.setProperty("spring.resource.loader.class", SpringResourceLoader.class
      .getName());
    velocityEngine.setProperty("spring.resource.loader.cache", "true");
    
    velocityEngine.setApplicationAttribute("spring.resource.loader", 
      getResourceLoader());
    velocityEngine.setApplicationAttribute("spring.resource.loader.path", resourceLoaderPath);
  }
  
  protected void postProcessVelocityEngine(VelocityEngine velocityEngine)
    throws IOException, VelocityException
  {}
}
