import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AppMaterialModule } from './shared/app-material/app-material.module';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { UserFormComponent } from './admin/users/user-form/user-form.component';
import { MultiformsComponent } from './admin/users/multiforms/multiforms.component';
import { DatePipe } from '@angular/common';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import { MAT_DATE_LOCALE } from '@angular/material/core';

registerLocaleData(localePt, 'pt')

@NgModule({
  declarations: [
    AppComponent,
    UserFormComponent,
    MultiformsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppMaterialModule,
  ],
  providers: [provideHttpClient(), provideAnimations(), DatePipe, 
    { provide: MAT_DATE_LOCALE, useValue: 'pt-PT'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
