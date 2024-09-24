import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { NavbarComponent } from './navbar/navbar.component';
import { provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import { LoginPageComponent } from './login-page/login-page.component';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
@NgModule({ 
    imports: [
        BrowserModule,
        AppRoutingModule
    ],
    declarations: [
        AppComponent,
        LandingPageComponent,
        NavbarComponent,
        LoginPageComponent
    ],
    bootstrap: [AppComponent], 
    providers: [provideHttpClient(withInterceptorsFromDi())] })
export class AppModule { }
