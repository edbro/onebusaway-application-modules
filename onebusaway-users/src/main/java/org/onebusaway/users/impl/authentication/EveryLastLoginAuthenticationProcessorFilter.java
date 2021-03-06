/**
 * Copyright (C) 2011 Brian Ferris <bdferris@onebusaway.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onebusaway.users.impl.authentication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.onebusaway.everylastlogin.server.AuthenticationResult;
import org.onebusaway.everylastlogin.server.LoginManager;
import org.onebusaway.everylastlogin.server.AuthenticationResult.EResultCode;
import org.onebusaway.users.model.IndexedUserDetails;
import org.onebusaway.users.services.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.FilterChainOrder;

public class EveryLastLoginAuthenticationProcessorFilter extends
    AbstractProcessingFilter {

  private CurrentUserService _currentUserService;

  @Autowired
  public void setCurrentUserService(CurrentUserService currentUserService) {
    _currentUserService = currentUserService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request)
      throws AuthenticationException {

    String mode = request.getParameter("mode");

    AuthenticationResult result = LoginManager.getResult(request);
    if (result == null)
      throw new EveryLastLoginAuthenticationException(
          "AuthenticationResult not found", mode);

    if (result.getCode() != EResultCode.SUCCESS)
      throw new EveryLastLoginAuthenticationException(
          "AuthenticationResult failure", mode);

    IndexedUserDetails details = _currentUserService.handleUserAction(
        result.getProvider(), result.getIdentity(), result.getCredentials(),
        false, mode);

    if (details == null)
      throw new EveryLastLoginAuthenticationException("could not get user details", mode);

    return new DefaultUserAuthenticationToken(details);
  }

  protected String determineFailureUrl(HttpServletRequest request,
      AuthenticationException failed) {

    String failureUrl = super.determineFailureUrl(request, failed);

    if (failed instanceof EveryLastLoginAuthenticationException) {
      EveryLastLoginAuthenticationException ex = (EveryLastLoginAuthenticationException) failed;
      String mode = ex.getMode();
      if (mode != null) {
        String prefix = "?";
        if (failureUrl.contains(prefix))
          prefix = "&";
        try {
          failureUrl += prefix + "mode=" + URLEncoder.encode(mode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
          throw new IllegalStateException(e);
        }
      }
    }

    return failureUrl;
  }

  @Override
  public String getDefaultFilterProcessesUrl() {
    return "/everylastlogin_login";
  }

  @Override
  public int getOrder() {
    return FilterChainOrder.AUTHENTICATION_PROCESSING_FILTER;
  }
}
