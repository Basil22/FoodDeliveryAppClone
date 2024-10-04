import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { ItemService } from './services/item.service';
import { FormsModule } from '@angular/forms';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async'; 
import { FilterItemsByVendorPipe } from './filter-items-by-vendor.pipe';
export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideClientHydration(),
    ItemService,
    
    BrowserModule,
    FormsModule,
    HttpClientModule, // Add HttpClientModule here
  
    provideHttpClient(withFetch()), provideAnimationsAsync(),
    FilterItemsByVendorPipe // Use this instead of HttpClientModule
  ],
};
