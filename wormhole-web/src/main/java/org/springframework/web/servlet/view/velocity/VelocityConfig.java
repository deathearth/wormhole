package org.springframework.web.servlet.view.velocity;

import org.apache.velocity.app.VelocityEngine;

@Deprecated
public abstract interface VelocityConfig
{
  public abstract VelocityEngine getVelocityEngine();
}
