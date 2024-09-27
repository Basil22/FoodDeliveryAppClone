import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from '../models/userDTO';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8083/api/users'; 

  constructor(private http: HttpClient) {}

  register(user: UserDTO ): Observable<any> {
    return this.http.post(`${this.apiUrl}/save`, user);
  }
    loginUser(loginData: { userPhoneNumber: string, userPassword: string }): Observable<any> {
    
    //return this.http.post(`${this.apiUrl}/login`, loginData);
   //return this.http.post('http://localhost:8083/api/users/login', loginData)
   const headers = new HttpHeaders().set('Content-Type', 'application/json');  // Ensure proper headersreturn this.http.post(`${this.apiUrl}/login`, loginData, { headers }); }
   return this.http.post(`${this.apiUrl}/login`, loginData, { headers });
  }

}
