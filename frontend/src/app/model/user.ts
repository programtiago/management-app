import { Department } from "./department"
import { UserEquipment } from "./user-equipment"
import { UserRole } from "./userRole"

export interface User {
    id: number,
    firstName: string,
    lastName: string,
    workNumber: number,
    birthdayDate?: string,
    department: Department,
    admissionDate: string,
    registryDate: string,
    isActive: boolean,
    userRole: UserRole,
    email: string,
    contactNumber: string
    updatedAt: string,
    password: string,
    userEquipments: UserEquipment[]
}
