import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { UserDTO } from '../models/userDTO';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8083/api/users';

  constructor(private http: HttpClient) {}

  register(user: UserDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/save`, user)
    .pipe(catchError((error) => {
      return throwError(() => error);
    }));
  }
  loginUser(loginData: {
    userPhoneNumber: string;
    userPassword: string;
  }): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json'); // Ensure proper headersreturn this.http.post(`${this.apiUrl}/login`, loginData, { headers }); }
    return this.http.post<{ message: string; userId: number }>(
      `${this.apiUrl}/login`,
      loginData,
      {
        headers,
        responseType: 'text' as 'json',
      }).pipe(catchError((error) => {
        return throwError(() => error);
      }));
  }
}
