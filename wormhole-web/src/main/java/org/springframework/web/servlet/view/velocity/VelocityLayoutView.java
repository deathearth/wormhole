package org.springframework.web.servlet.view.velocity;

import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.core.NestedIOException;

@Deprecated
public class VelocityLayoutView
  extends VelocityToolboxView
{
  public static final String DEFAULT_LAYOUT_URL = "layout.vm";
  public static final String DEFAULT_LAYOUT_KEY = "layout";
  public static final String DEFAULT_SCREEN_CONTENT_KEY = "screen_content";
  private String layoutUrl = "layout.vm";
  private String layoutKey = "layout";
  private String screenContentKey = "screen_content";
  
  public void setLayoutUrl(String layoutUrl)
  {
    this.layoutUrl = layoutUrl;
  }
  
  public void setLayoutKey(String layoutKey)
  {
    this.layoutKey = layoutKey;
  }
  
  public void setScreenContentKey(String screenContentKey)
  {
    this.screenContentKey = screenContentKey;
  }
  
  public boolean checkResource(Locale locale)
    throws Exception
  {
    if (!super.checkResource(locale)) {
      return false;
    }
    try
    {
      getTemplate(this.layoutUrl);
      return true;
    }
    catch (ResourceNotFoundException ex)
    {
      throw new NestedIOException("Cannot find Velocity template for URL [" + this.layoutUrl + "]: Did you specify the correct resource loader path?", ex);
    }
    catch (Exception ex)
    {
      throw new NestedIOException("Could not load Velocity template for URL [" + this.layoutUrl + "]", ex);
    }
  }
  
  protected void doRender(Context context, HttpServletResponse response)
    throws Exception
  {
    renderScreenContent(context);
    
    String layoutUrlToUse = (String)context.get(this.layoutKey);
    if (layoutUrlToUse != null)
    {
      if (this.logger.isDebugEnabled()) {
        this.logger.debug("Screen content template has requested layout [" + layoutUrlToUse + "]");
      }
    }
    else {
      layoutUrlToUse = this.layoutUrl;
    }
    mergeTemplate(getTemplate(layoutUrlToUse), context, response);
  }
  
  private void renderScreenContent(Context velocityContext)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Rendering screen content template [" + getUrl() + "]");
    }
    StringWriter sw = new StringWriter();
    Template screenContentTemplate = getTemplate(getUrl());
    screenContentTemplate.merge(velocityContext, sw);
    
    velocityContext.put(this.screenContentKey, sw.toString());
  }
}
