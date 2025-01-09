import { UserRole } from "./userRole";

export interface CreateUserAssignEquipmentRequest{
        id: number,
        firstName: string,
        lastName: string,
        birthdayDate: string,
        nif: string,
        email: string,
        registryDate: string,
        contactNumber: string,
        workNumber: number,
        workStatus: string,
        isAvailableForVacation: boolean,
        isActive: boolean,
        admissionDate: string,
        recruitmentCompany: string,
        userRole: UserRole,
        password: string
}