import {useCallback, useEffect, useState} from "react";
import MUIDataTable from "mui-datatables";

const Datatable = (props) => {


    const columns = [
        {
            name: "id",
            label: "Id",
            options: {
                filter: true,
                sort: true,
            }
        },
        {
            name: "title",
            label: "Title",
            options: {
                filter: true,
                sort: false,
            }
        },
        {
            name: "artist",
            label: "Artist",
            options: {
                filter: true,
                sort: false,
            }
        },
        {
            name: "album",
            label: "Album",
            options: {
                filter: true,
                sort: false,
                // display: false,
            }
        },
    ];

    const options = {
        pagination: false,
        filterType: 'textField',
        responsive: 'simple',
        tableBodyHeight: '50vh',
        onRowClick: rowData => props.onClick(rowData[0], rowData[1]),
    };

    return (
        <MUIDataTable
            title={"MP3 List"}
            data={props.mp3List}
            columns={columns}
            options={options}
        />
    )
}

export default Datatable;