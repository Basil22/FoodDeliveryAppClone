import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { ItemService } from './services/item.service';
import { FormsModule } from '@angular/forms'; 
export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideClientHydration(),
    ItemService,
    
    BrowserModule,
    FormsModule,
    HttpClientModule, // Add HttpClientModule here
  
    provideHttpClient(withFetch()) // Use this instead of HttpClientModule
  ],
};
