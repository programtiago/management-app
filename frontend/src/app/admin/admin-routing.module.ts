import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsersComponent } from './users/users.component';
import { MultiformsComponent } from './users/multiforms/multiforms.component';

const routes: Routes = [
  { path: '', component: UsersComponent},
  { path: 'new', component: MultiformsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
