import { Department } from "../department/department"
import { UserDepartment } from "../department/user-department/user-department"
import { UserEquipment } from "../user-equipment/user-equipment"
import { UserRole } from "./userRole"

export interface User {
    id: number,
    firstName: string,
    lastName: string,
    workNumber: number,
    recruitmentCompany: string, 
    birthdayDate: Date,
    nif: string,
    department: Department,
    admissionDate: string,
    registryDate: string,
    isActive: boolean,
    userRole: UserRole,
    email: string,
    contactNumber: string
    updatedAt: string,
    password: string,
    departmentId: number,
    userEquipments: UserEquipment[],
    userDepartments: UserDepartment[]
}
