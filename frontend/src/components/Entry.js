import React from 'react'
import { FaTimes } from 'react-icons/fa'

const Entry = ({ entry, onDelete }) => {
  return (
    <div className='entry'>
      <h3>{entry.description} 
        <FaTimes style={{ color:'red', cursor: 'pointer'}} onClick={() => onDelete(entry.id)}/>
      </h3>
      <img src={`/images/${entry.id}`} alt="img" width="400" height="400"/>
    </div>
  )
}

export default Entry
