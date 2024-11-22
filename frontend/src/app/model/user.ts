import { UserRole } from "./userRole"

export interface User {
    _id: string,
    firstName: string,
    lastName: string,
    workNumber: number,
    birthdayDate?: string,
    department: string,
    admissionDate: string,
    registryDate: string,
    active: boolean,
    userRole: UserRole,
    email: string,
    contactNumber: string
    updatedAt: string
}
