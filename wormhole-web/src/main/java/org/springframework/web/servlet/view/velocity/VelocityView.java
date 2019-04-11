package org.springframework.web.servlet.view.velocity;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.NestedIOException;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.springframework.web.util.NestedServletException;

@Deprecated
public class VelocityView
  extends AbstractTemplateView
{
  private Map<String, Class<?>> toolAttributes;
  private String dateToolAttribute;
  private String numberToolAttribute;
  private String encoding;
  private boolean cacheTemplate = false;
  private VelocityEngine velocityEngine;
  private Template template;
  
  public void setToolAttributes(Map<String, Class<?>> toolAttributes)
  {
    this.toolAttributes = toolAttributes;
  }
  
  public void setDateToolAttribute(String dateToolAttribute)
  {
    this.dateToolAttribute = dateToolAttribute;
  }
  
  public void setNumberToolAttribute(String numberToolAttribute)
  {
    this.numberToolAttribute = numberToolAttribute;
  }
  
  public void setEncoding(String encoding)
  {
    this.encoding = encoding;
  }
  
  protected String getEncoding()
  {
    return this.encoding;
  }
  
  public void setCacheTemplate(boolean cacheTemplate)
  {
    this.cacheTemplate = cacheTemplate;
  }
  
  protected boolean isCacheTemplate()
  {
    return this.cacheTemplate;
  }
  
  public void setVelocityEngine(VelocityEngine velocityEngine)
  {
    this.velocityEngine = velocityEngine;
  }
  
  protected VelocityEngine getVelocityEngine()
  {
    return this.velocityEngine;
  }
  
  protected void initApplicationContext()
    throws BeansException
  {
    super.initApplicationContext();
    if (getVelocityEngine() == null) {
      setVelocityEngine(autodetectVelocityEngine());
    }
  }
  
  protected VelocityEngine autodetectVelocityEngine()
    throws BeansException
  {
    try
    {
      VelocityConfig velocityConfig = (VelocityConfig)BeanFactoryUtils.beanOfTypeIncludingAncestors(
        getApplicationContext(), VelocityConfig.class, true, false);
      return velocityConfig.getVelocityEngine();
    }
    catch (NoSuchBeanDefinitionException ex)
    {
      throw new ApplicationContextException("Must define a single VelocityConfig bean in this web application context (may be inherited): VelocityConfigurer is the usual implementation. This bean may be given any name.", ex);
    }
  }
  
  public boolean checkResource(Locale locale)
    throws Exception
  {
    try
    {
      this.template = getTemplate(getUrl());
      return true;
    }
    catch (ResourceNotFoundException ex)
    {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("No Velocity view found for URL: " + getUrl());
      }
      return false;
    }
    catch (Exception ex)
    {
      throw new NestedIOException("Could not load Velocity template for URL [" + getUrl() + "]", ex);
    }
  }
  
  protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    exposeHelpers(model, request);
    
    Context velocityContext = createVelocityContext(model, request, response);
    exposeHelpers(velocityContext, request, response);
    exposeToolAttributes(velocityContext, request);
    
    doRender(velocityContext, response);
  }
  
  protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request)
    throws Exception
  {}
  
  protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return createVelocityContext(model);
  }
  
  protected Context createVelocityContext(Map<String, Object> model)
    throws Exception
  {
    return new VelocityContext(model);
  }
  
  protected void exposeHelpers(Context velocityContext, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    exposeHelpers(velocityContext, request);
  }
  
  protected void exposeHelpers(Context velocityContext, HttpServletRequest request)
    throws Exception
  {}
  
  @SuppressWarnings("rawtypes")
  protected void exposeToolAttributes(Context velocityContext, HttpServletRequest request)
    throws Exception
  {
    if (this.toolAttributes != null) {
      for (Map.Entry<String, Class<?>> entry : this.toolAttributes.entrySet())
      {
        String attributeName = (String)entry.getKey();
        Class<?> toolClass = (Class)entry.getValue();
        try
        {
          Object tool = toolClass.newInstance();
          initTool(tool, velocityContext);
          velocityContext.put(attributeName, tool);
        }
        catch (Exception ex)
        {
          throw new NestedServletException("Could not instantiate Velocity tool '" + attributeName + "'", ex);
        }
      }
    }
    if ((this.dateToolAttribute != null) || (this.numberToolAttribute != null))
    {
      if (this.dateToolAttribute != null) {
        velocityContext.put(this.dateToolAttribute, new LocaleAwareDateTool(request));
      }
      if (this.numberToolAttribute != null) {
        velocityContext.put(this.numberToolAttribute, new LocaleAwareNumberTool(request));
      }
    }
  }
  
  protected void initTool(Object tool, Context velocityContext)
    throws Exception
  {}
  
  protected void doRender(Context context, HttpServletResponse response)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Rendering Velocity template [" + getUrl() + "] in VelocityView '" + getBeanName() + "'");
    }
    mergeTemplate(getTemplate(), context, response);
  }
  
  protected Template getTemplate()
    throws Exception
  {
    if ((isCacheTemplate()) && (this.template != null)) {
      return this.template;
    }
    return getTemplate(getUrl());
  }
  
  protected Template getTemplate(String name)
    throws Exception
  {
    return getEncoding() != null ? 
      getVelocityEngine().getTemplate(name, getEncoding()) : 
      getVelocityEngine().getTemplate(name);
  }
  
  protected void mergeTemplate(Template template, Context context, HttpServletResponse response)
    throws Exception
  {
    try
    {
      template.merge(context, response.getWriter());
    }
    catch (MethodInvocationException ex)
    {
      Throwable cause = ex.getWrappedThrowable();
      
      throw new NestedServletException("Method invocation failed during rendering of Velocity view with name '" + getBeanName() + "': " + ex.getMessage() + "; reference [" + ex.getReferenceName() + "], method '" + ex.getMethodName() + "'", cause == null ? ex : cause);
    }
  }
  
  private static class LocaleAwareDateTool
    extends DateTool
  {
    private final HttpServletRequest request;
    
    public LocaleAwareDateTool(HttpServletRequest request)
    {
      this.request = request;
    }
    
    public Locale getLocale()
    {
      return RequestContextUtils.getLocale(this.request);
    }
    
    public TimeZone getTimeZone()
    {
      TimeZone timeZone = RequestContextUtils.getTimeZone(this.request);
      return timeZone != null ? timeZone : super.getTimeZone();
    }
  }
  
  private static class LocaleAwareNumberTool
    extends NumberTool
  {
    private final HttpServletRequest request;
    
    public LocaleAwareNumberTool(HttpServletRequest request)
    {
      this.request = request;
    }
    
    public Locale getLocale()
    {
      return RequestContextUtils.getLocale(this.request);
    }
  }
}
