import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Subscription } from 'rxjs';
import { UserLoginService } from './sp-service/login/user-login.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'sp';

  isLoggedIn = false;
  private subscription!: Subscription;

  private auth = inject(UserLoginService);

  constructor(private router: Router, private service: UserLoginService) { }

  ngOnInit(): void {
    this.subscription = this.service.isLoggedIn$.subscribe(status => {
      this.isLoggedIn = status;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }


}
