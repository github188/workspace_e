
function encode_base64( what )
{
	var base64_encodetable = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var result = "";
	var len = what.length;
	var x, y;
	var ptr = 0;

	while( len-- > 0 )
	{
		x = what.charCodeAt( ptr++ );
		result += base64_encodetable.charAt( ( x >> 2 ) & 63 );

		if( len-- <= 0 )
		{
			result += base64_encodetable.charAt( ( x << 4 ) & 63 );
			result += "==";
			break;
		}

		y = what.charCodeAt( ptr++ );
		result += base64_encodetable.charAt( ( ( x << 4 ) | ( ( y >> 4 ) & 15 ) ) & 63 );

		if ( len-- <= 0 )
		{
			result += base64_encodetable.charAt( ( y << 2 ) & 63 );
			result += "=";
			break;
		}

		x = what.charCodeAt( ptr++ );
		result += base64_encodetable.charAt( ( ( y << 2 ) | ( ( x >> 6 ) & 3 ) ) & 63 );
		result += base64_encodetable.charAt( x & 63 );

	}

	return result;
}


