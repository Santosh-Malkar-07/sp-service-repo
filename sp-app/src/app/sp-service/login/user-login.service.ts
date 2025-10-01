import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserLogin } from '../../sp-classes/user-login';
import { Router } from '@angular/router';
import { Injectable, computed, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserLoginService {
 url: string = "http://localhost:8080/userApi/login";

  constructor(private http: HttpClient, private router: Router) { }

  //calling to backend service to generate token
  getJwtToken(userLogin: UserLogin): Observable<string> {
    return this.http.post(this.url, userLogin, { responseType: 'text' });
  }

  private tokenKey = 'token';
  private isLoggedIn = new BehaviorSubject<boolean>(this.hasToken());
  isLoggedIn$ = this.isLoggedIn.asObservable();

  login(token: string) {
    if (this.isBrowser()) {
      localStorage.setItem(this.tokenKey, token);
      this.isLoggedIn.next(true);
    }
  }

  logout() {
    if (this.isBrowser()) {
      localStorage.removeItem(this.tokenKey);
      this.isLoggedIn.next(false);
    }
  }

  private hasToken(): boolean {
    return this.isBrowser() && !!localStorage.getItem(this.tokenKey);
  }

  getToken(): string | null {
    return this.isBrowser() ? localStorage.getItem(this.tokenKey) : null;
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined' && !!window.localStorage;
  }
}
