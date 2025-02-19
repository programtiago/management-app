export interface HistoryLog{
    id: number,
    entitysId: number[],
    action: string,
    actionDateTime: Date,
    username: string,
    entity: string
}