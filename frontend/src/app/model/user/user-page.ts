import { User } from "./user";

export interface UserPage{
    content: User[],
    totalElements: number,
    totalPages: number
}