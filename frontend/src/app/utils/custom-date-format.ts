export const MY_DATE_FORMATS = {
    parse: {
        dateInput: ['DD/MM/YYYY'],
    },
    display: {
        dateInput: (date: any) => date.format('DD/MM/YYYY'),
    },
};
