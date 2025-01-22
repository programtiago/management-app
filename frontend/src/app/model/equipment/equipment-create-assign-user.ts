import { User } from "../user/user";
import { StatusEquipment } from "./status-equipment";

export interface CreateEquipmentAssignUserRequest{
    id: number,
    serialNumber: string,
    description: string,
    brand: string,
    model: string,
    category: string,
    user: User,
    unity: string,
    registryDate: string,
    statusEquipment: StatusEquipment
}