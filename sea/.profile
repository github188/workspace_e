
# @(#)B.11.31_LR       

# Default user .profile file (/usr/bin/sh initialization).

# Set up the terminal:
	if [ "$TERM" = "" ]
	then
		eval ` tset -s -Q -m ':?hp' `
	else
		eval ` tset -s -Q `
	fi
	stty erase "^H" kill "^U" intr "^C" eof "^D"
	stty hupcl ixon ixoff
	tabs

# NOTE: '.' is added to $PATH for compatibility reasons only. This
#       default will be changed in a future release. If "." is not
#       needed for compatibility it is better to omit this line. 
#	Please edit .profile according to your site requirements.

# Set up the search paths:
	PATH=$PATH:.

# Set up the shell environment:
	set -u
	trap "echo 'logout'" 0

# Set up the shell variables:
	EDITOR=vi
	export EDITOR



export ORACLE_BASE=/oracle
export ORACLE_HOME=$ORACLE_BASE/product/10.2.0/db
export ORA_CRS_HOME=$ORACLE_BASE/product/10.2.0/crs
export ORACLE_SID=sea1
export ORACLE_TERM=xterm
export LD_LIBRARY_PATH=$ORACLE_HOME/lib:/lib:/usr/lib:$ORACLE_HOME/rdbms/lib
export CLASSPATH=$ORACLE_HOME/JRE:$ORACLE_HOME/jlib:$ORACLE_HOME/rdbms/jlib/:$ORACLE_HOME/network/jlib
export PATH=$PATH:$ORACLE_HOME/bin:$ORA_CRS_HOME/bin
umask 022

