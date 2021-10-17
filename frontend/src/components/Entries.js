import React from 'react'
import Entry from './Entry'

const Entries = ({ entries, onDelete }) => {
  return (
    <>
      {entries.length > 0 
        ? entries.map((entry) => (
          <Entry key={entry.id} entry={entry} onDelete={onDelete}/>
        ))
        : "No Entries"
        }
    </>
  )
}

export default Entries
