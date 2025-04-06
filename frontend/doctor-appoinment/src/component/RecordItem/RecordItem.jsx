import React from "react";
import "./RecordItem.css"

const RecordItem = (props) => {
    const entries = Object.entries(props.data);

    return (
        <>
            {entries.map(([_, value], index) => (
                <td key={index}>
                    {typeof value === "object" && value !== null
                        ? JSON.stringify(value)
                        : value}
                </td>
            ))}
        </>
    );
}

export default RecordItem;