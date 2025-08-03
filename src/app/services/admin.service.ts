import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Admin } from '../models/admin.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  register(admin: Admin): Observable<Admin> {
    return this.http.post<Admin>(`${this.baseUrl}/registeradmin`, admin);
  }

  login(admin: Admin): Observable<Admin> {
    return this.http.post<Admin>(`${this.baseUrl}/loginadmin`, admin);
  }

  getAdmins(): Observable<Admin[]> {
    return this.http.get<Admin[]>(`${this.baseUrl}/adminlist`);
  }
}
