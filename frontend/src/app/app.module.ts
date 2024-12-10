import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AppMaterialModule } from './shared/app-material/app-material.module';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { UserFormComponent } from './admin/users/user-form/user-form.component';
import { MultiformsComponent } from './admin/users/multiforms/multiforms.component';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    UserFormComponent,
    MultiformsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppMaterialModule
  ],
  providers: [provideHttpClient(), provideAnimations(), DatePipe, 
    { provide: MAT_DATE_LOCALE, useValue: 'pt-PT'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
