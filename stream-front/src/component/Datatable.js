import {useCallback, useEffect, useState} from "react";
import MUIDataTable from "mui-datatables";

const Datatable = () => {

    const [mp3List, setMp3List] = useState([]);

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


    useEffect(async () => {
        const response = await fetch("/mp3/list", {
            headers : {
                'Content-Type': 'application/json',
                'Accept': 'application/json'}
            });
        if (!response.ok){
            throw new Error('뭔가 잘못된 거 같아');
        }

        const data = await response.json();
        console.log(data);
        const list = data.map(mp3Data => {
            return{
                id: mp3Data.id,
                title: mp3Data.title,
                artist: mp3Data.artist,
                album: mp3Data.album
            };
        });

        console.log(list);
        setMp3List(list);
    }, []);

    const data = [
        { name: "Joe James", company: "Test Corp", city: "Yonkers", state: "NY" },
        { name: "John Walsh", company: "Test Corp", city: "Hartford", state: "CT" },
        { name: "Bob Herm", company: "Test Corp", city: "Tampa", state: "FL" },
        { name: "James Houston", company: "Test Corp", city: "Dallas", state: "TX" },
    ];

    const options = {
        pagination: false,
        filterType: 'textField',
    };

    return (
        <div>
        <MUIDataTable
            title={"MP3 List"}
            data={mp3List}
            columns={columns}
            options={options}
        />
        </div>
    )
}

export default Datatable;