package org.springframework.web.servlet.view.velocity;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

@Deprecated
public class VelocityViewResolver
  extends AbstractTemplateViewResolver
{
  private String dateToolAttribute;
  private String numberToolAttribute;
  private String toolboxConfigLocation;
  
  public VelocityViewResolver()
  {
    setViewClass(requiredViewClass());
  }
  
  protected Class<?> requiredViewClass()
  {
    return VelocityView.class;
  }
  
  public void setDateToolAttribute(String dateToolAttribute)
  {
    this.dateToolAttribute = dateToolAttribute;
  }
  
  public void setNumberToolAttribute(String numberToolAttribute)
  {
    this.numberToolAttribute = numberToolAttribute;
  }
  
  public void setToolboxConfigLocation(String toolboxConfigLocation)
  {
    this.toolboxConfigLocation = toolboxConfigLocation;
  }
  
  protected void initApplicationContext()
  {
    super.initApplicationContext();
    if (this.toolboxConfigLocation != null) {
      if (VelocityView.class == getViewClass())
      {
        this.logger.info("Using VelocityToolboxView instead of default VelocityView due to specified toolboxConfigLocation");
        
        setViewClass(VelocityToolboxView.class);
      }
      else if (!VelocityToolboxView.class.isAssignableFrom(getViewClass()))
      {
        throw new IllegalArgumentException("Given view class [" + getViewClass().getName() + "] is not of type [" + VelocityToolboxView.class.getName() + "], which it needs to be in case of a specified toolboxConfigLocation");
      }
    }
  }
  
  protected AbstractUrlBasedView buildView(String viewName)
    throws Exception
  {
    VelocityView view = (VelocityView)super.buildView(viewName);
    view.setDateToolAttribute(this.dateToolAttribute);
    view.setNumberToolAttribute(this.numberToolAttribute);
    if (this.toolboxConfigLocation != null) {
      ((VelocityToolboxView)view).setToolboxConfigLocation(this.toolboxConfigLocation);
    }
    return view;
  }
}
