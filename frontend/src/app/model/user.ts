import { UserRole } from "./userRole"

export interface User {
    _id: string,
    firstName: string,
    lastName: string,
    workNumber: number,
    department: string,
    registryDate: string,
    isActive: boolean,
    userRole: UserRole,
    email: string,
    contactNumber: string
    updatedAt: string
}
