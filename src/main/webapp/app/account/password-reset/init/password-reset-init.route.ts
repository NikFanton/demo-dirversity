import { Route } from '@angular/router';

import { PasswordResetInitComponent } from './password-reset-init.component';

export const passwordResetInitRoute: Route = {
  path: 'reset/request',
  component: PasswordResetInitComponent,
  data: {
    authorities: ['ROLE_ADMIN'],
    pageTitle: 'global.menu.account.password'
  }
};
