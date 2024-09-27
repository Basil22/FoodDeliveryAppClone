import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterationFormComponent } from './registeration-form/registeration-form.component';

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path:'login', component:LoginFormComponent},
    {path:'register', component:RegisterationFormComponent}
];
