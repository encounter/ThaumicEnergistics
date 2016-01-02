package thaumicenergistics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumicenergistics.common.ThaumicEnergistics;
import thaumicenergistics.gui.ThEGuiHandler;
import thaumicenergistics.registries.BlockEnum;
import thaumicenergistics.texture.BlockTextureManager;
import thaumicenergistics.tileentities.TileDistillationEncoder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDistillationEncoder
	extends AbstractBlockAEWrenchable
{
	public BlockDistillationEncoder()
	{
		// Call super with material machine (iron) 
		super( Material.iron );

		// Basic hardness
		this.setHardness( 1.0f );

		// Sound of metal
		this.setStepSound( Block.soundTypeMetal );

		// Place in the ThE creative tab
		this.setCreativeTab( ThaumicEnergistics.ThETab );
	}

	@Override
	protected final boolean onBlockActivated( final World world, final int x, final int y, final int z, final EntityPlayer player )
	{
		// Launch the gui.
		ThEGuiHandler.launchGui( ThEGuiHandler.DISTILLATION_ENCODER, player, world, x, y, z );

		return true;
	}

	/**
	 * Creates the tile entity associated with the block.
	 */
	@Override
	public TileEntity createNewTileEntity( final World world, final int metaData )
	{
		return new TileDistillationEncoder();
	}

	/**
	 * Gets the standard block icon.
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon( final int side, final int meta )
	{
		// Face?
		if( side == ForgeDirection.OPPOSITES[meta] )
		{
			// Face texture
			return BlockTextureManager.DISTILLATION_ENCODER.getTextures()[1];
		}

		// Top or bottom?
		if( ( ForgeDirection.VALID_DIRECTIONS[side] == ForgeDirection.UP )
						|| ( ForgeDirection.VALID_DIRECTIONS[side] == ForgeDirection.DOWN ) )
		{
			// Bottom texture
			return BlockTextureManager.DISTILLATION_ENCODER.getTextures()[2];

		}

		// Sides
		return BlockTextureManager.DISTILLATION_ENCODER.getTextures()[0];
	}

	/**
	 * Gets the unlocalized name of the block.
	 */
	@Override
	public String getUnlocalizedName()
	{
		return BlockEnum.DISTILLATION_ENCODER.getUnlocalizedName();
	}

	/**
	 * Is opaque.
	 */
	@Override
	public final boolean isOpaqueCube()
	{
		// Occlude adjoining faces.
		return true;
	}

	/**
	 * Is solid.
	 */
	@Override
	public final boolean isSideSolid( final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection side )
	{
		// This is a solid cube
		return true;
	}

	@Override
	public void onBlockPlacedBy( final World world, final int x, final int y, final int z, final EntityLivingBase player, final ItemStack itemStack )
	{
		// Set the metadata to up
		world.setBlockMetadataWithNotify( x, y, z, 0, 2 );
	}

	/**
	 * Taken care of by texture manager
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public final void registerBlockIcons( final IIconRegister register )
	{
		// Ignored
	}

	/**
	 * Normal renderer.
	 */
	@Override
	public final boolean renderAsNormalBlock()
	{
		return true;
	}

	@Override
	public boolean rotateBlock( final World world, final int x, final int y, final int z, final ForgeDirection side )
	{
		// Get and increment the meta data
		int metaData = world.getBlockMetadata( x, y, z ) + 1;

		// Bounds check
		if( metaData >= ForgeDirection.VALID_DIRECTIONS.length )
		{
			metaData = 0;
		}

		// Set the meta data
		world.setBlockMetadataWithNotify( x, y, z, metaData, 3 );

		return true;
	}

}
