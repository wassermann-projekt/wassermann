function b=binaryadd(d,f,n)
    
    function l=len(x)
        l=1+floor(log(x)/log(2));
    end

    if d<0
        d=d+2^n;
    end
    
    if f<0
        f=f+2^n;
    end
        
    if and(n>=len(d),n>=len(f))
        
        bind=[];
        binf=[];
        
        for z=0:1:n-1
            bind=[mod(d,2), bind];
            d=floor(d/2);
        end
        
        for z=0:1:n-1
            binf=[mod(f,2), binf];
            f=floor(f/2);
        end
        
        bins=bind+binf;
        
        uet=0;
        
        for z=0:1:n-1
                                 
            if uet==0
                
                if bins(n-z)==0
                    uet=0;
                elseif bins(n-z)==1
                    uet=0;
                elseif bins(n-z)==2
                    uet=1;
                    bins(n-z)=0;
                end
                
            elseif uet==1
                
                if bins(n-z)==0
                    bins(n-z)=1;
                    uet=0;
                elseif bins(n-z)==1
                    bins(n-z)=0;
                    uet=1;
                elseif bins(n-z)==2
                    bins(n-z)=1;
                    uet=1;
                end
                
            end
            
        end    
        
    else
        error('Vorgesehene Stellenzahl zu gering, bitte mehr Stellen.')
    
    end
    
    bins
    
end