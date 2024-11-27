import { UserRole } from "./userRole"

export interface User {
    id: number,
    firstName: string,
    lastName: string,
    workNumber: number,
    birthdayDate?: string,
    department: string,
    admissionDate: string,
    registryDate: string,
    isActive: boolean,
    userRole: UserRole,
    email: string,
    contactNumber: string
    updatedAt: string,
    password: string
}
