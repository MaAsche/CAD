import React from 'react'
import Button from './Button'

const Header = ({onAdd, showAddEntry}) => {
	return (
		<header className='header'>
			<h1>CAD Blog</h1>
			<Button text={ showAddEntry? "CANCEL" : "ADD"} onClick={onAdd} />
		</header>
	)
}

export default Header
