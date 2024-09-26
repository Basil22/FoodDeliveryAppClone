import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  loginUser(userPhoneNumber: string, userPassword: string): Observable<any> {
    const loginData = { userPhoneNumber, userPassword };
    return this.http.post(`${this.apiUrl}/login`, loginData);
  }

  registerUser(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/save`, userData);
  }
}
