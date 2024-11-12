import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin/admin.component';
import { UsersListComponent } from './users-list/users-list.component';

import { AppRoutingModule } from '../app-routing.module';
import { AppMaterialModule } from '../shared/app-material/app-material.module';


@NgModule({
  declarations: [
    AdminComponent,
    UsersListComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    AppMaterialModule
  ]
})
export class AdminModule { }
