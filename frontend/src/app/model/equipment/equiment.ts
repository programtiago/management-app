import { UserEquipment } from "../user-equipment/user-equipment";
import { StatusEquipment } from "./status-equipment";

export interface Equipment{
    id: number,
    description: string,
    serialNumber: string,
    macAddress: string,
    brand: string,
    model: string,
    location: string,
    function: string,
    unity: string,
    type: string,
    registryDate: string,
    allocationDateTime: Date,
    returningDateTime: Date,
    statusEquipment: StatusEquipment,
    finalCondition: string,
    usersEquipments: UserEquipment[]
}