import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  console.log('Interceptor URL:', req.url);
  console.log('Interceptor token:', token);

  if (!token) {
    return next(req);
  }

  const reqComToken = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(reqComToken);
};