import { UserEquipment } from "./user-equipment";

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
    registryDate: string,
    allocationDateTime: Date,
    returningDateTime: Date,
    statusEquipment: string,
    finalCondition: string,
    usersEquipments: UserEquipment[]
}