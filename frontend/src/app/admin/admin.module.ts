import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin/admin.component';
import { UsersListComponent } from './users/users-list/users-list.component';

import { AppMaterialModule } from '../shared/app-material/app-material.module';
import { UsersComponent } from './users/users.component';
import { SharedModule } from '../shared/shared.module';
import { ModalInformationComponent } from './modal-information/modal-information.component';
import { ModalDeleteuserInfoComponent } from './users/modal-deleteuser-info/modal-deleteuser-info.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DepartmentsComponent } from './departments/departments.component';
import { DepartmentFormComponent } from './departments/department-form/department-form.component';
import { DepartmentsListComponent } from './departments/departments-list/departments-list.component';
import { ModalInfoDeleteComponent } from './departments/modal-info-delete/modal-info-delete.component';
import { ModalUsersAssignmentEquipmentComponent } from './users/modal-users-assignment-equipment/modal-users-assignment-equipment.component';

@NgModule({
  declarations: [
    AdminComponent,
    UsersListComponent,
    UsersComponent,
    ModalInformationComponent,
    ModalDeleteuserInfoComponent,
    DashboardComponent,
    DepartmentsComponent,
    DepartmentFormComponent,
    DepartmentsListComponent,
    ModalInfoDeleteComponent,
    ModalUsersAssignmentEquipmentComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    AppMaterialModule,
    SharedModule
  ]
})
export class AdminModule { }
