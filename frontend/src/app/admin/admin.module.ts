import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin/admin.component';
import { UsersListComponent } from './users-list/users-list.component';

import { MatTableModule } from '@angular/material/table';


@NgModule({
  declarations: [
    AdminComponent,
    UsersListComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatTableModule
  ]
})
export class AdminModule { }
