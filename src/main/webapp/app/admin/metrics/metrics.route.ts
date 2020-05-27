import { Route } from '@angular/router';

import { JhiMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
  path: '',
  component: JhiMetricsMonitoringComponent,
  data: {
    authorities: ['ROLE_ADMIN', 'ROLE_SYSTEM_ADMIN'],
    pageTitle: 'metrics.title'
  }
};
