package org.springframework.web.servlet.view.velocity;

import org.springframework.web.servlet.view.AbstractUrlBasedView;

@Deprecated
public class VelocityLayoutViewResolver
  extends VelocityViewResolver
{
  private String layoutUrl;
  private String layoutKey;
  private String screenContentKey;
  
  protected Class<?> requiredViewClass()
  {
    return VelocityLayoutView.class;
  }
  
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
  
  protected AbstractUrlBasedView buildView(String viewName)
    throws Exception
  {
    VelocityLayoutView view = (VelocityLayoutView)super.buildView(viewName);
    if (this.layoutUrl != null) {
      view.setLayoutUrl(this.layoutUrl);
    }
    if (this.layoutKey != null) {
      view.setLayoutKey(this.layoutKey);
    }
    if (this.screenContentKey != null) {
      view.setScreenContentKey(this.screenContentKey);
    }
    return view;
  }
}
