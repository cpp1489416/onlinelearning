
drop function if exists `get_categories_parent_list`;
#create function parent

delimiter ;;

create function `get_categories_parent_list`(curId int)
returns varchar(1000)
begin
	declare stemp varchar(1000);
    declare stempparent varchar(1000);
    set stemp = "0";
    set stempparent = curId;

    outer_label: begin #fdsafdsa
		while stempparent != 0 and stempparent is not null do
			set stemp = concat(stemp, ",", stempparent);
			select parentId  into stempparent from course_category where id = stempparent;
			if stempparent = curId then
				leave outer_label;
			end if;
		end while;
    end outer_label;

    return stemp;
end
;;
delimiter ;


# create function children
drop function if exists `get_categories_children_list`;

delimiter ;;
create function `get_categories_children_list` (curId int)
returns varchar(1000)
begin
	declare stemp varchar(1000);
    declare stempchild varchar(1000);
    set stemp = "$";
    set stempchild = curId;

    while stempchild is not null do
		set stemp = concat(stemp, ",", stempchild);
        select group_concat(id) into stempchild from course_category where find_in_set(parentId, stempchild);
    end while;
    return stemp;
end
;;
delimiter ;

set @a = "fdaf";

select get_categories_parent_list(71);