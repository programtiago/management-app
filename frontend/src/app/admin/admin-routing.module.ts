import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsersComponent } from './users/users.component';
import { MultiformsComponent } from './users/multiforms/multiforms.component';
import { UserFormComponent } from './users/user-form/user-form.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DepartmentsComponent } from './departments/departments.component';

const routes: Routes = [
  { path: '', component: DashboardComponent},
  { path: 'users', component: UsersComponent},
  { path: 'new', component: MultiformsComponent},
  { path: 'edit/:id', component: UserFormComponent},
  { path: 'departments', component: DepartmentsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
